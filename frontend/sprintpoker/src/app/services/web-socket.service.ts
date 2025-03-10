import {Injectable, OnInit} from '@angular/core';
import SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {Player} from '../modules/model/Player';

@Injectable({
	providedIn: 'root'
})
export class WebSocketService {

	socketClient: Stomp.Client;

	constructor() {
		let ws = new SockJS('http://localhost:8080/ws');
		this.socketClient = Stomp.over(ws);
	}

	public connect(player?:Player) : void {
		this.socketClient.connect({}, this.onConnect, this.onError);
		this.socketClient.send(
			'/api/player.playerJoined',
			{},
			JSON.stringify(player)
		)
	}

	private onConnect() {
		this.socketClient.subscribe('/topic/public')
		this.socketClient.send('')
	}

	private onError() {
		console.log("Error while connecting with server over WebSocket")
	}
}
