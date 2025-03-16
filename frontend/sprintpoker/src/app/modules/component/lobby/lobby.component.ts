import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {WelcomePageComponent} from '../welcome-page/welcome-page.component';
import {PlayerService as _playerService} from '../../../services/player/player.service'
import {NgForOf, NgIf} from '@angular/common';
import {EstimationComponent} from '../estimation/estimation.component';
import {PlayerStatus} from '../../model/Player';

@Component({
	selector: 'app-lobby',
	imports: [
		FormsModule,
		WelcomePageComponent,
		NgForOf,
		NgIf,
		EstimationComponent,
	],
	templateUrl: './lobby.component.html',
	standalone: true,
	styleUrl: './lobby.component.scss'
})
export class LobbyComponent {

	protected readonly _playerService = _playerService;
	isConnected: boolean = false;

	protected readonly PlayerStatus = PlayerStatus;
}
