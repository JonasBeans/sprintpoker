import {Component, inject} from '@angular/core';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {Fibonacci} from './fibonacci';
import {WebSocketService} from '../../../services/web-socket.service';
import {PlayerService} from '../../../services/player/player.service';

@Component({
	selector: 'app-estimation',
	imports: [
		NgForOf,
		NgClass,
	],
	standalone: true,
	templateUrl: './estimation.component.html',
	styleUrl: './estimation.component.scss'
})
export class EstimationComponent {

	_websocketService: WebSocketService = inject(WebSocketService);
	protected readonly PlayerService = PlayerService;

	fibonacciSequence: number[] = Fibonacci.getSequence(10);
	isEstimated: boolean = false;
	chosenNumber?: number = undefined;

	chooseEstimate(fibonacciNumber: number) {
		this.chosenNumber = fibonacciNumber;
		this.isEstimated = true;
	}

	resetChoice() {
		console.info("reset")
		this.isEstimated = false;
		this.chosenNumber = undefined;
		this._websocketService.sendEstimationResetMessage();
	};

	determineButtonState(fibonacciNumber: number): boolean {
		if (this.chosenNumber === undefined) return true;
		return (this.chosenNumber === fibonacciNumber);
	}

	makeEstimation() {
		console.info("confirm")
		this._websocketService.sendEstimationMessage(this.chosenNumber);
	}

}

