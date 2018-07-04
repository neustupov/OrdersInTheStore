import {PriceRequest} from "./price-request.model";

export class User {

    id: number;
    name: string;
    email: string;
    password: string;
    enabled: boolean;
    roles: string[];
    priceRequests: PriceRequest[];
    registered: string;


    constructor(id: number, name: string, email: string, password: string, enabled: boolean, roles: string[],
                priceRequests: PriceRequest[], registered: string) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
        this.priceRequests = priceRequests;
        this.registered = registered;
    }
}