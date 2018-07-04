import {Injectable} from "@angular/core";
import {Http, RequestOptionsArgs} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {basePath, priceRequestPath, reqOptions, reqOptionsJson} from "../shared/config";
import {PriceRequest} from "../component/model/price-request.model";

@Injectable()
export class PriceRequestService {

    constructor(private http: Http,
                private dateTimeTransformer: DateTimeTransformer) {
    }

    loadAllPriceRequests(): Observable<PriceRequest[]> {
        return this.http.get(basePath + priceRequestPath, reqOptions)
            .map((res) => {
                return this.mapResponse(res);
            });
    }

    deletePriceRequest(meal: PriceRequest): Observable<Response> {
        return this.http.delete(basePath + priceRequestPath + '/' + meal.id, reqOptions);
    }

    mapResponse(resp) {
        let priceRequestList: PriceRequest[] = resp.json();
        priceRequestList.forEach((priceRequest: PriceRequest) => {
            priceRequest.addDateTime = this.dateTimeTransformer.deserializeDateTime(priceRequest.addDateTime);
        });
        return priceRequestList;
    }

    save(priceRequest: PriceRequest): Observable<Response> {
        priceRequest.addDateTime = this.dateTimeTransformer.serializeDateTime(priceRequest.addDateTime);
        if (priceRequest.id) {
            return this.update(priceRequest);
        }
        else {
            return this.http.post(basePath + priceRequestPath, JSON.stringify(priceRequest), reqOptionsJson);
        }
    }

    private update(priceRequest: PriceRequest): Observable<Response> {
        return this.http.put(basePath + priceRequestPath + '/' + priceRequest.id, JSON.stringify(priceRequest), reqOptionsJson);
    }

    getFilteredDataSet(startDate: Date, endDate: Date, startTime: Date, endTime: Date) {
        let getParams = new URLSearchParams();

        if (startDate != null) {
            getParams.set('startDate', this.dateTimeTransformer.serializeDate(startDate));
        }
        if (endDate != null) {
            getParams.set('endDate', this.dateTimeTransformer.serializeDate(endDate));
        }
        if (startTime != null) {
            getParams.set('startTime', this.dateTimeTransformer.serializeTime(startTime));
        }
        if (endTime != null) {
            getParams.set('endTime', this.dateTimeTransformer.serializeTime(endTime));
        }

        var clone: RequestOptionsArgs = Object.assign({}, reqOptions);
        clone.search = getParams;

        return this.http.get(basePath + priceRequestPath + '/' + 'filter', clone).map((res) => {
            return this.mapResponse(res);
        });
    }
}