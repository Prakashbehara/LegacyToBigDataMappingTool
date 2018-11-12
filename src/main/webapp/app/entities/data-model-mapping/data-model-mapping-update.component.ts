import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDataModelMapping } from 'app/shared/model/data-model-mapping.model';
import { DataModelMappingService } from './data-model-mapping.service';
import { IDataModel } from 'app/shared/model/data-model.model';
import { DataModelService } from 'app/entities/data-model';

@Component({
    selector: 'jhi-data-model-mapping-update',
    templateUrl: './data-model-mapping-update.component.html'
})
export class DataModelMappingUpdateComponent implements OnInit {
    private _dataModelMapping: IDataModelMapping;
    isSaving: boolean;

    datamodels: IDataModel[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private dataModelMappingService: DataModelMappingService,
        private dataModelService: DataModelService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dataModelMapping }) => {
            this.dataModelMapping = dataModelMapping;
        });
        this.dataModelService.query().subscribe(
            (res: HttpResponse<IDataModel[]>) => {
                this.datamodels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dataModelMapping.id !== undefined) {
            this.subscribeToSaveResponse(this.dataModelMappingService.update(this.dataModelMapping));
        } else {
            this.subscribeToSaveResponse(this.dataModelMappingService.create(this.dataModelMapping));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDataModelMapping>>) {
        result.subscribe((res: HttpResponse<IDataModelMapping>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDataModelById(index: number, item: IDataModel) {
        return item.id;
    }
    get dataModelMapping() {
        return this._dataModelMapping;
    }

    set dataModelMapping(dataModelMapping: IDataModelMapping) {
        this._dataModelMapping = dataModelMapping;
    }
}
