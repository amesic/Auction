import { Injectable } from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
 webSocketEndPoint: string ="http://localhost:8080/socket";
 //webSocketEndPoint: string ="https://atlantbh-auction-api.herokuapp.com/socket";
  stompClient: any;
// Open connection with the back-end socket
 
connect() {
  let webSocket = new SockJS(this.webSocketEndPoint);
  this.stompClient = Stomp.over(webSocket);
  return this.stompClient;
}
_disconnect() {
  if (this.stompClient != null) {
    this.stompClient.disconnect();
  }
}

}
