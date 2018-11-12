import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IDataAsset } from 'app/shared/model/data-asset.model';
import { DataAssetService } from './data-asset.service';
import { IApplication } from 'app/shared/model/application.model';
import { ApplicationService } from 'app/entities/application';
import { ISourceFeed } from 'app/shared/model/source-feed.model';
import { SourceFeedService } from 'app/entities/source-feed';
import { ISourceDatabase } from 'app/shared/model/source-database.model';
import { SourceDatabaseService } from 'app/entities/source-database';

@Component({
    selector: 'jhi-data-asset-update',
    templateUrl: './data-asset-update.component.html'
})
export class DataAssetUpdateComponent implements OnInit {
    private _dataAsset: IDataAsset;
    isSaving: boolean;

    applications: IApplication[];

    sourcefeeds: ISourceFeed[];

    sourcedatabases: ISourceDatabase[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private dataAssetService: DataAssetService,
        private applicationService: ApplicationService,
        private sourceFeedService: SourceFeedService,
        private sourceDatabaseService: SourceDatabaseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dataAsset }) => {
            this.dataAsset = dataAsset;
        });
        this.applicationService.query().subscribe(
            (res: HttpResponse<IApplication[]>) => {
                this.applications = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.sourceFeedService.query().subscribe(
            (res: HttpResponse<ISourceFeed[]>) => {
                this.sourcefeeds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.sourceDatabaseService.query().subscribe(
            (res: HttpResponse<ISourceDatabase[]>) => {
                this.sourcedatabases = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dataAsset.id !== undefined) {
            this.subscribeToSaveResponse(this.dataAssetService.update(this.dataAsset));
        } else {
            this.subscribeToSaveResponse(this.dataAssetService.create(this.dataAsset));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDataAsset>>) {
        result.subscribe((res: HttpResponse<IDataAsset>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackApplicationById(index: number, item: IApplication) {
        return item.id;
    }

    trackSourceFeedById(index: number, item: ISourceFeed) {
        return item.id;
    }

    trackSourceDatabaseById(index: number, item: ISourceDatabase) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get dataAsset() {
        return this._dataAsset;
    }

    set dataAsset(dataAsset: IDataAsset) {
        this._dataAsset = dataAsset;
    }
}
