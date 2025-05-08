import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActiveUserListComponent } from './active-user-list.component';

describe('ActiveUserListComponent', () => {
  let component: ActiveUserListComponent;
  let fixture: ComponentFixture<ActiveUserListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ActiveUserListComponent]
    });
    fixture = TestBed.createComponent(ActiveUserListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
