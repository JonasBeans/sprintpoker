import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {WelcomePageComponent} from '../welcome-page/welcome-page.component';

@Component({
	selector: 'app-lobby',
	imports: [
		FormsModule,
		WelcomePageComponent
	],
	templateUrl: './lobby.component.html',
	standalone: true,
	styleUrl: './lobby.component.css'
})
export class LobbyComponent {


}
