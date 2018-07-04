import {PriceRequest} from "./price-request.model";
import {Client} from "./client.model";
export class Order {

    id: number;
    client: Client;
    addDateTime;
    prepayment: number;
    amount: number;
    ready: boolean;
    priceRequests: PriceRequest[];


    constructor(id: number, client: Client, addDateTime, prepayment: number, amount: number, ready: boolean,
                priceRequests: PriceRequest[]) {
        this.id = id;
        this.client = client;
        this.addDateTime = addDateTime;
        this.prepayment = prepayment;
        this.amount = amount;
        this.ready = ready;
        this.priceRequests = priceRequests;
    }
}