import { Component } from '@angular/core';
import { MessageContent, MessageType } from 'src/app/core/interfaces/message-content';
import { MessageRoom } from 'src/app/core/interfaces/message-room';
import { User } from 'src/app/core/interfaces/user';
import { MessageContentService } from 'src/app/core/services/message-content.service';
import { MessageRoomService } from 'src/app/core/services/message-room.service';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.scss']
})
export class MessagesComponent {
  /**
   * 1. connect to websocket server /api/ws
   * 2. subscribe /topic/active
   * 3. send connect to other users /app/user/connect
   */

  currentUser: User = {};
  activeUsersSubscription: any;
  isShowDialogChat: boolean = false;
  selectedMessageRoom: MessageRoom = {};
  messageToSend: MessageContent = {};

  constructor(
    private userService: UserService,
    private messageRoomService: MessageRoomService,
    private messageContentService: MessageContentService
  ) { }

  ngOnInit() {
    this.currentUser = this.userService.getFromLocalStorage() as User;
    this.userService.connect(this.currentUser);
    this.messageContentService.connect(this.currentUser);

    window.addEventListener('beforeunload', () => {
      this.userService.disconnect(this.currentUser);

    })
    this.subscribeMessages();
  }

  ngOnDestroy() {
    this.userService.disconnect(this.currentUser);
    this.messageContentService.disconnect(this.currentUser);
  }

  chat(selectedUsers: User[]) {
    console.log(selectedUsers);
    this.isShowDialogChat = false;

    const usernames = selectedUsers.map(u => u.username).filter((u): u is string => u !== undefined);
    if (this.currentUser.username) usernames.push(this.currentUser.username);

    this.messageRoomService.findMessageRoomByMembers(usernames).subscribe({
      next: (foundMessageRoom: MessageRoom) => {
        console.log('foundMessageRoom', foundMessageRoom);
        // not found
        if (!foundMessageRoom) {
          if (!this.currentUser.username) return;
          // create
          this.messageRoomService.createChatRoom(this.currentUser.username, usernames).subscribe({
            next: (createdMessageRoom: MessageRoom) => {
              console.log('createdMessageRoom', createdMessageRoom);
            },
            error: (error) => {
              console.log(error);
            }
          });
        }
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  selectMessageRoom(room: MessageRoom) {
    console.log(room);

    this.selectedMessageRoom = room;
    this.getMessagesByRoomId();
  }

  getMessagesByRoomId() {
    this.messageContentService.getMessagesByRoomId(this.selectedMessageRoom.id).subscribe({
      next: (messages: MessageContent[]) => {
        this.selectedMessageRoom.messages = messages;
      }, error: (error: any) => {
        console.log(error);
      }
    })
  }

  subscribeMessages() {
    this.messageContentService.subscribeMessagesObservable().subscribe({
      next: (messageContent: MessageContent) => {
        console.log('messageContent', messageContent);
        this.selectedMessageRoom.messages?.push(messageContent);
      }, error: (error: any) => {
        console.log(error);
      }
    })
  }

  sendMessage() {
    this.messageToSend = {
      content: this.messageToSend.content,
      messageRoomId: this.selectedMessageRoom.id,
      sender: this.currentUser.username,
      messageType: MessageType.TEXT
    }

    this.messageContentService.sendMessage(this.messageToSend);
    console.log('this.messageToSend', this.messageToSend);
    this.messageToSend = {};
  }
}