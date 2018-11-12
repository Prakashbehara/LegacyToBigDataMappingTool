import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDataModel } from 'app/shared/model/data-model.model';
import { DataModelService } from './data-model.service';

@Component({
    selector: 'jhi-data-model-update',
    templateUrl: './data-model-update.component.html'
})
export class DataModelUpdateComponent implements OnInit {
    private _dataModel: IDataModel;
    isSaving: boolean;

    constructor(private dataModelService: DataModelService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dataModel }) => {
            this.dataModel = dataModel;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dataModel.id !== undefined) {
            this.subscribeToSaveResponse(this.dataModelService.update(this.dataModel));
        } else {
            this.subscribeToSaveResponse(this.dataModelService.create(this.dataModel));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDataModel>>) {
        result.subscribe((res: HttpResponse<IDataModel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get dataModel() {
        return this._dataModel;
    }

    set dataModel(dataModel: IDataModel) {
        this._dataModel = dataModel;
    }
}
