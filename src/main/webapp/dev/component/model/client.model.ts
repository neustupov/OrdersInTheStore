import {PriceRequest} from "./price-request.model";
import {Order} from "./order.model";
export class Client {

    id: number;
    name: string;
    lastName: string;
    phoneNumber: number;
    priceRequests: PriceRequest[];
    orders: Order[];


    constructor(id: number, name: string, lastName: string, phoneNumber: number, priceRequests: PriceRequest[],
                orders: Order[]) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.priceRequests = priceRequests;
        this.orders = orders;
    }
}