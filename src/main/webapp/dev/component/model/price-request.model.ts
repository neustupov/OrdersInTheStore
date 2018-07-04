import {User} from "./user.model";
import {Order} from "./order.model";
import {Client} from "./client.model";
import {Product} from "./product.model";

export class PriceRequest {

    id: number;
    user: User;
    addDateTime;
    order: Order;
    client: Client;
    ready: boolean;
    products: Product[];

    constructor(id: number, user, addDateTime, order, client, ready: boolean, products) {
        this.user = user;
        this.addDateTime = addDateTime;
        this.order = order;
        this.client = client;
        this.ready = ready;
        this.products = products;
    }
}