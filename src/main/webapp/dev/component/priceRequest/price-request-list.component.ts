import {Component, OnInit, ViewChild} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {FormBuilder} from "@angular/forms";

@Component({
    templateUrl: '../../../templates/priceRequest/price-request-list.html'
})
export class PriceRequestListComponent implements OnInit {

    startDate: Date;
    endDate: Date;
    startTime: Date;
    endTime: Date;

    priceRequestsHolder: Observable<PriceRequest[]>;

    @ViewChild(EditPriceRequestComponent)
    private editPriceRequestChild: EditPriceRequestComponent;

    constructor(private priceRequestService: PriceRequestService,
                private formBuilder: FormBuilder) {
    }

    ngOnInit() {
        this.priceRequestsHolder = this.priceRequestService.loadAllPriceRequests();
    }

    updateList() {
        this.priceRequestsHolder = this.priceRequestService.loadAllPriceRequests();
    }

    deleteItem(priceRequest: PriceRequest) {
        this.priceRequestService.deletePriceRequest(priceRequest).subscribe(res => {
            this.updateList();
        });
    }

    showCreateModal() {
        this.editPriceRequestChild.resetForm();
        this.editPriceRequestChild.showToggle = true;
    }

    selectPriceRequest(priceRequest) {
        this.editPriceRequestChild.showToggle = true;
        this.editPriceRequestChild.fillMyForm(priceRequest.data);
    }

    save(priceRequest: PriceRequest) {
        this.editPriceRequestChild.showToggle = false;
        this.priceRequestService.save(priceRequest).subscribe(
            res => {
                this.updateList();
            }
        );
    }

    onFilter() {
        this.priceRequestsHolder = this.priceRequestService.getFilteredDataSet(
            this.startDate,
            this.endDate,
            this.startTime,
            this.endTime);
    }

    clearFilters() {
        this.startDate = null;
        this.endDate = null;
        this.startTime = null;
        this.endTime = null;
        this.updateList();
    }

    getRowClass(rowData: PriceRequest, rowIndex): string {
        return rowData.exceed ? "exceeded" : null;
    }
}