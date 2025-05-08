import { Component, Input } from '@angular/core';
import { MessageRoom } from 'src/app/core/interfaces/message-room';
import { User } from 'src/app/core/interfaces/user';

@Component({
  selector: 'app-conversation',
  templateUrl: './conversation.component.html',
  styleUrls: ['./conversation.component.scss']
})
export class ConversationComponent {
  @Input() room: MessageRoom = {};
  @Input() currentUser: User = {};
  @Input() selectedMessageRoomId: string | undefined= '';

}

//last message
//room name

//3 trg hop
// 1. nhom - room da co ten => ten nhom
// 2. nhom - room chua co ten => danh sach member ngan cach boi dau phay
// 3. chi co 2 nguoi => hien thi ten cua nguoi con lai

//===> string