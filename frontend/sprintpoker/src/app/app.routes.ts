import { Routes } from '@angular/router';
import {LobbyComponent} from "./modules/component/lobby/lobby.component";
import {WelcomePageComponent} from './modules/component/welcome-page/welcome-page.component';

export const routes: Routes = [
	{path: 'lobby', component: LobbyComponent},
	{path: '', component: LobbyComponent}
];
