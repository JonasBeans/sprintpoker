import {Component, inject} from '@angular/core';
import {WebSocketService} from '../../../services/web-socket.service';
import {Player} from '../../model/Player';
import {FormsModule} from '@angular/forms';
import {NgIf} from '@angular/common';

@Component({
	selector: 'app-welcome-page',
	imports: [
		FormsModule,
		NgIf
	],
	templateUrl: './welcome-page.component.html',
	standalone: true,
	styleUrl: './welcome-page.component.css'
})
export class WelcomePageComponent {

	private _webSocketService: WebSocketService = inject(WebSocketService);

	player: Player = {username: ''};
	isConnected = false;

	public connectPlayer() :void {
		this._webSocketService.connect(this.player);
		this.isConnected = true;
	}

	public disconnectPlayer() : void {
		this._webSocketService.disconnect();
		this.isConnected = false;
	}

}
