import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataModelMapping } from 'app/shared/model/data-model-mapping.model';

@Component({
    selector: 'jhi-data-model-mapping-detail',
    templateUrl: './data-model-mapping-detail.component.html'
})
export class DataModelMappingDetailComponent implements OnInit {
    dataModelMapping: IDataModelMapping;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dataModelMapping }) => {
            this.dataModelMapping = dataModelMapping;
        });
    }

    previousState() {
        window.history.back();
    }
}
