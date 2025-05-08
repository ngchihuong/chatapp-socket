import { Component, Input } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Status, User } from 'src/app/core/interfaces/user';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-active-user-list',
  templateUrl: './active-user-list.component.html',
  styleUrls: ['./active-user-list.component.scss']
})
export class ActiveUserListComponent {
  @Input() currentUser: User = {};

  activeUsers: User[] = [];
  activeUsersSubscription: any;

  constructor(private userService: UserService) { }
  async ngOnInit() {
    this.activeUsers = await lastValueFrom(this.userService.getOnlineUsers());
    this.activeUsers?.forEach((user: User) => {
      if (user.username) {
        this.userService.activeUsers[user.username] = "ONLINE";
      }
    })
    this.subscribeActiveUsers();
  }
  ngOndestroy() {
    this.activeUsersSubscription?.unsubscribe();
  }

  subscribeActiveUsers() {
    this.activeUsersSubscription = this.userService.subscribesActiveUsers().subscribe({
      next: (user: User) => {
        if (user.status === Status.OFFLINE) {
          this.activeUsers = this.activeUsers.filter((u: User) => u.username !== user.username);
          if (user.username) {
            delete this.userService.activeUsers[user.username];
          }
        } else if (!this.activeUsers.some(existingUser => existingUser.username === user.username)) {
          this.activeUsers = [...this.activeUsers, user];
          if (user.username) this.userService.activeUsers[user.username] = "ONLINE";
        }
      },
      error(err) {
        console.log(err);
      }
    })
  }
}
