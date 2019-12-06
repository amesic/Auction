import { Injectable } from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  webSocketEndPoint: string ="http://localhost:8080/socket";
  stompClient: any;
// Open connection with the back-end socket
 
connect() {
  let webSocket = new SockJS(this.webSocketEndPoint);
  this.stompClient = Stomp.over(webSocket);
  return this.stompClient;
}
_disconnect(id, email) {
  try {
    let object = {
      "email": email,
      "productId" : id
    }
    console.log("fired")
    this.stompClient.send("/app/send/message/disconnect" , {}, JSON.stringify(object));
  } catch (error) {
    console.log(error);
  }
  if (this.stompClient != null) {
    this.stompClient.disconnect();
  }
  console.log("Disconnected");
}

}
