import { Injectable } from '@angular/core';
import { User } from '../interfaces/user';
import { Observable, Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { CompatClient, Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = environment.apiUrl + environment.apiVersion + 'users';
  private webSocketUrl = environment.apiUrl + environment.webSocketUrl;
  private stompClient: CompatClient = {} as CompatClient;
  private subscriptionActiveUsers: any = {};
  activeUsers: {
    [key: string]: string
  } = {
      ONLINE: "ONLINE",
      OFFLINE: "OFFLINE"
    }

  private activeUsersSubject = new Subject<User>();

  constructor(
    private http: HttpClient,
  ) { }


  login(user: User): Observable<User> {
    return this.http.post(this.apiUrl, user);
  }
  saveToLocalStorage(response: User) {
    localStorage.setItem("user", JSON.stringify({
      username: response.username,
      avatarUrl: response.avatarUrl
    }));
  }

  getFromLocalStorage(): User | null {
    return JSON.parse(localStorage.getItem("user") ?? '{}');
  }

  connect(user: User) {
    const socket = new SockJS(this.webSocketUrl);
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect(
      {},
      () => this.onConnect(user),
      (error: any) => console.log(error)
    );
  }

  private onConnect(user: User) {
    this.subscribeActive();
    this.sendConnect(user);
  }
  disconnect(user: User) {
    this.sendDisconnect(user);
    this.stompClient.disconnect(() => {
      console.log("Disconnected from WebSocket");
    });
    this.subscriptionActiveUsers?.unsubscribe();
  }

  private subscribeActive() {
    this.subscriptionActiveUsers = this.stompClient.subscribe('/topic/active', (message: any) => {
      const user = JSON.parse(message.body);
      console.log(user);

      this.activeUsersSubject.next(user);
    })
  }
  sendConnect(user: User) {
    this.stompClient.send(
      "/app/user/connect",
      {},
      JSON.stringify(user)
    )
  }
  sendDisconnect(user: User) {
    this.stompClient.send(
      "/app/user/disconnect",
      {},
      JSON.stringify(user)
    )
  }
  subscribesActiveUsers(): Observable<User> {
    return this.activeUsersSubject.asObservable();
  }

  getOnlineUsers(): Observable<User[]> {
    const url = this.apiUrl + '/online';
    return this.http.get<User[]>(url);
  }

  getUserStatus(username: string): boolean {
    if (!username) return false;
    return this.activeUsers[username] === "ONLINE";
  }

  searchUsersByUsername(username: string) {
    const url = this.apiUrl + '/search/' + username;
    return this.http.get<User[]>(url);
  }
}