export interface Player {
	username:string;
	status: PlayerStatus
	estimation: number;
}

export enum PlayerStatus {
	MADE_ESTIMATION = "MADE_ESTIMATION",
	IS_ESTIMATING = "IS_ESTIMATING"
}
