import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AutoCompleteCompleteEvent } from 'primeng/autocomplete';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-select-users-dialog',
  templateUrl: './select-users-dialog.component.html',
  styleUrls: ['./select-users-dialog.component.scss']
})
export class SelectUsersDialogComponent {
  @Input() visible: boolean = false;
  @Input() currentUsername: string | undefined = '';

  @Output() onHideEvent = new EventEmitter();
  @Output() onSubmitEvent = new EventEmitter();

  selectedUsers: any[] = [];
  searchedUsers: any[] = [];

constructor(private userService: UserService) {}

  onSearch(event: AutoCompleteCompleteEvent) {
    console.log(event);
    
    this.userService.searchUsersByUsername(event.query).subscribe({
      next: (users) => {
        const selectedUsers = this.selectedUsers.map((user) => user.username);
        this.searchedUsers = users.filter((user) => {
          return !selectedUsers.includes(user.username);
        })
      },
      error: (error) => {
        console.log(error);
      }
    })
  }
  onHide() {
    this.onHideEvent.emit();
  }
  onSubmit() {
    this.onSubmitEvent.emit(this.selectedUsers);
    this.selectedUsers = [];
  }
}
