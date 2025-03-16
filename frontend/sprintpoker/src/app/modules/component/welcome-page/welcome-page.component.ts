import {Component, EventEmitter, inject, Input, Output} from '@angular/core';
import {WebSocketService} from '../../../services/web-socket.service';
import {Player, PlayerStatus} from '../../model/Player';
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
	styleUrl: './welcome-page.component.scss'
})
export class WelcomePageComponent {

	@Input() isConnected!: boolean;
	@Output() connectionEvent = new EventEmitter<boolean>();

	private _webSocketService: WebSocketService = inject(WebSocketService);

	player: Player = {username: '', status: PlayerStatus.IS_ESTIMATING, estimation: 0};

	public connectPlayer() :void {
		this._webSocketService.connect(this.player);
		this.isConnected = true;
		this.connectionEvent.emit(this.isConnected);
	}

	public disconnectPlayer() : void {
		this._webSocketService.disconnect();
		this.isConnected = false;
		this.connectionEvent.emit(this.isConnected);
	}

}
