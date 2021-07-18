export class UserData {
private userName: string;
private firstName: string;
private middleName: string;
private lastName: string;
private email: string;
private isFistTime: boolean;
private timezone: string;
private isActive: boolean;
private isNotLocked: boolean;
private profileImageUrl: string;
private role: any;

    constructor(){
        this.userName = '';
        this.firstName = '';
        this.middleName = '';
        this.lastName = '';
        this.email = '';
        this.isFistTime = false;
        this.timezone = '';
        this.isActive = false;
        this.isNotLocked = false;
    }
}