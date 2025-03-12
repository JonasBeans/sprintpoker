import {Injectable} from '@angular/core';
import {Player} from '../../modules/model/Player';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

	public static activePlayers: Player[] = [{username: 'Jhonny Doe'}];

	constructor() { }

}
