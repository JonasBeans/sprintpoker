import {Component, inject} from '@angular/core';
import {WebSocketService} from '../../../services/web-socket.service';
import {Player} from '../../model/Player';
import {FormsModule} from '@angular/forms';

@Component({
	selector: 'app-welcome-page',
	imports: [
		FormsModule
	],
	templateUrl: './welcome-page.component.html',
	standalone: true,
	styleUrl: './welcome-page.component.css'
})
export class WelcomePageComponent {

	private _webSocketService: WebSocketService = inject(WebSocketService);
	player: Player = {username: ''};

	public connectPlayer() :void {
		this._webSocketService.connect(this.player);
	}

	public disconnectPlayer() : void {
		this._webSocketService.disconnect();
	}

}
