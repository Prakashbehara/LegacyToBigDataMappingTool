import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataModel } from 'app/shared/model/data-model.model';

@Component({
    selector: 'jhi-data-model-detail',
    templateUrl: './data-model-detail.component.html'
})
export class DataModelDetailComponent implements OnInit {
    dataModel: IDataModel;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dataModel }) => {
            this.dataModel = dataModel;
        });
    }

    previousState() {
        window.history.back();
    }
}
