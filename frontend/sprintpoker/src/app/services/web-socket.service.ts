import {Injectable} from '@angular/core';
import SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {Player, PlayerStatus} from '../modules/model/Player';
import {PlayerService} from './player/player.service';
// @ts-ignore
import {environment} from '../../environments/environments'

@Injectable({
	providedIn: 'root'
})
export class WebSocketService {

	socketClient: Stomp.Client | undefined;

	public connect(player: Player): void {
		let ws = new SockJS(`${environment.host}/ws`);
		this.socketClient = Stomp.over(ws);
		this.socketClient.connect({}, this.onConnected(player) , this.onError());
	}

	private onConnected(player: Player) {
		return () => {
			console.log("Connected to STOMP server");

			this.socketClient?.subscribe('/topic/players.updates', (message) => {
				let receivedPlayers: Player[] = JSON.parse(message.body);
				PlayerService.activePlayers = [...receivedPlayers];
			});

			this.socketClient?.subscribe('/topic/players.estimationUpdates', (message) => {
				let receivedPlayers: Player[] = JSON.parse(message.body);
				PlayerService.activePlayers = [...receivedPlayers];
			});

			this.socketClient?.subscribe('/topic/estimation.isRevealable', (message) => {
				PlayerService.isRevealable = JSON.parse(message.body);
			})

			// Send a message to the correct STOMP destination
			this.socketClient?.send(
				"/app/player.playerJoined", // Correct STOMP endpoint
				{},
				JSON.stringify({username: player.username, status: PlayerStatus.IS_ESTIMATING, estimation: 0}) // Proper JSON formatting
			);
		}
	}

	sendEstimationMessage(estimation?: number) {
		this.socketClient?.send(
			"/app/player.madeEstimation",
			{},
			JSON.stringify({estimation: estimation})
		)
	}

	sendEstimationResetMessage() {
		this.socketClient?.send(
			"/app/player.resetEstimation"
		)
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
