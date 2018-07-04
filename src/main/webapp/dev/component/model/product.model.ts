import {PriceRequest} from "./price-request.model";
import {Type} from "./type.model";
import {Model} from "./model.model";
import {Brand} from "./brand.model";

export class Product {

    id: number;
    priceRequests: PriceRequest[];
    addDate;
    price: number;
    type: Type;
    model: Model;
    brand: Brand;


    constructor(id: number, priceRequests: PriceRequest[], addDate, price: number, type: Type, model: Model,
                brand: Brand) {
        this.id = id;
        this.priceRequests = priceRequests;
        this.addDate = addDate;
        this.price = price;
        this.type = type;
        this.model = model;
        this.brand = brand;
    }
}