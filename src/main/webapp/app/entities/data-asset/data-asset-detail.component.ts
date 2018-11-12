import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDataAsset } from 'app/shared/model/data-asset.model';

@Component({
    selector: 'jhi-data-asset-detail',
    templateUrl: './data-asset-detail.component.html'
})
export class DataAssetDetailComponent implements OnInit {
    dataAsset: IDataAsset;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dataAsset }) => {
            this.dataAsset = dataAsset;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
