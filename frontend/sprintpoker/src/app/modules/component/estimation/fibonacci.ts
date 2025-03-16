export class Fibonacci {

	public static getSequence(time: number): number[] {
		return this.calculateSequence([], time);
	}

	private static calculateSequence(sequence: number[], times: number): number[] {
		if (times == 0) return sequence;
		let length = sequence.length;
		if (length === 0) {
			sequence = [1];
		} else if (length === 1) {
			sequence = [1, 2];
		} else {
			sequence.push(sequence[length - 1] + sequence[length - 2])
		}
		return this.calculateSequence(sequence, times - 1);
	}
}
