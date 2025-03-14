import {Component, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {WelcomePageComponent} from '../welcome-page/welcome-page.component';
import {PlayerService as _playerService} from '../../../services/player/player.service'
import {NgForOf, NgIf} from '@angular/common';

@Component({
	selector: 'app-lobby',
	imports: [
		FormsModule,
		WelcomePageComponent,
		NgForOf,
		NgIf
	],
	templateUrl: './lobby.component.html',
	standalone: true,
	styleUrl: './lobby.component.css'
})
export class LobbyComponent {

	protected readonly _playerService = _playerService;
	isConnected: boolean = false;

}
