import {Component, inject, OnInit} from '@angular/core';
import {WebSocketService} from '../../../services/web-socket.service';
import {FormsModule} from '@angular/forms';
import {Player} from '../../model/Player';
import {routes} from '../../../app.routes';
import {Router} from '@angular/router';

@Component({
	selector: 'app-lobby',
	imports: [
		FormsModule
	],
	templateUrl: './lobby.component.html',
	standalone: true,
	styleUrl: './lobby.component.css'
})
export class LobbyComponent {

	private _webSocketService: WebSocketService = inject(WebSocketService);
	player: Player;

	constructor(private router: Router) {
		this.player = {username: ''}
	}

	public connectPlayer() :void {
		this._webSocketService.connect(this.player);
	}

}
