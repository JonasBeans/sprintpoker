import {Component, inject} from '@angular/core';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {Fibonacci} from './fibonacci';
import {WebSocketService} from '../../../services/web-socket.service';

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

	fibonacciSequence: number[] = Fibonacci.getSequence(10);
	isEstimated: boolean = false;
	chosenNumber?: number = undefined;
	_websocketService: WebSocketService = inject(WebSocketService);

	chooseEstimate(fibonacciNumber: number) {
		this.chosenNumber = fibonacciNumber;
		this.isEstimated = true;
	}

	resetChoice() {
		this.isEstimated = false;
		this.chosenNumber = undefined;
		this._websocketService.sendEstimationResetMessage();
	};

	determineButtonState(fibonacciNumber: number): boolean {
		if (this.chosenNumber === undefined) return true;
		return (this.chosenNumber === fibonacciNumber);
	}

	makeEstimation() {
		this._websocketService.sendEstimationMessage(this.chosenNumber);
	}
}

