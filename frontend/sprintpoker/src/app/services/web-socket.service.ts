import {ChangeDetectorRef, inject, Injectable} from '@angular/core';
import SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {Player} from '../modules/model/Player';
import {PlayerService} from './player/player.service';

@Injectable({
	providedIn: 'root'
})
export class WebSocketService {

	socketClient: Stomp.Client | undefined;

	public connect(player: Player): void {
		let ws = new SockJS('http://localhost:8080/ws');
		this.socketClient = Stomp.over(ws);
		this.socketClient.connect({}, this.onConnected(player) , this.onError());
	}

	private onConnected(player: Player) {
		return () => {
			console.log("Connected to STOMP server");

			// Subscribe to a topic and handle incoming messages
			this.socketClient?.subscribe('/topic/public', (message) => {
				console.log("Received:", message.body);
			});

			this.socketClient?.subscribe('/topic/players.Updates', (message) => {
				let receivedPlayers: Player[] = JSON.parse(message.body);
				PlayerService.activePlayers = [...receivedPlayers];
			});

			// Send a message to the correct STOMP destination
			this.socketClient?.send(
				"/app/player.playerJoined", // Correct STOMP endpoint
				{},
				JSON.stringify({username: player.username}) // Proper JSON formatting
			);
		}
	}

	disconnect() {
		this.socketClient?.disconnect(() => {console.log("Disconnect")})
		PlayerService.activePlayers = [];
	}

	private onError() {
		return () => {
			console.log("Error while connecting with server over WebSocket")
		}
	}

}
