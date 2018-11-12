import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISourceFeedMapping } from 'app/shared/model/source-feed-mapping.model';
import { SourceFeedMappingService } from './source-feed-mapping.service';
import { ISourceFeed } from 'app/shared/model/source-feed.model';
import { SourceFeedService } from 'app/entities/source-feed';
import { IDataModelMapping } from 'app/shared/model/data-model-mapping.model';
import { DataModelMappingService } from 'app/entities/data-model-mapping';
import { ISourceDatabaseMapping } from 'app/shared/model/source-database-mapping.model';
import { SourceDatabaseMappingService } from 'app/entities/source-database-mapping';
import { IDataAsset } from 'app/shared/model/data-asset.model';
import { DataAssetService } from 'app/entities/data-asset';

@Component({
    selector: 'jhi-source-feed-mapping-update',
    templateUrl: './source-feed-mapping-update.component.html'
})
export class SourceFeedMappingUpdateComponent implements OnInit {
    private _sourceFeedMapping: ISourceFeedMapping;
    isSaving: boolean;

    sourcefeeds: ISourceFeed[];

    datamodelmappings: IDataModelMapping[];

    sourcedatabasemappings: ISourceDatabaseMapping[];

    dataassets: IDataAsset[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private sourceFeedMappingService: SourceFeedMappingService,
        private sourceFeedService: SourceFeedService,
        private dataModelMappingService: DataModelMappingService,
        private sourceDatabaseMappingService: SourceDatabaseMappingService,
        private dataAssetService: DataAssetService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sourceFeedMapping }) => {
            this.sourceFeedMapping = sourceFeedMapping;
        });
        this.sourceFeedService.query().subscribe(
            (res: HttpResponse<ISourceFeed[]>) => {
                this.sourcefeeds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.dataModelMappingService.query({ 'sourceFeedMappingId.specified': 'false' }).subscribe(
            (res: HttpResponse<IDataModelMapping[]>) => {
                if (!this.sourceFeedMapping.dataModelMapping || !this.sourceFeedMapping.dataModelMapping.id) {
                    this.datamodelmappings = res.body;
                } else {
                    this.dataModelMappingService.find(this.sourceFeedMapping.dataModelMapping.id).subscribe(
                        (subRes: HttpResponse<IDataModelMapping>) => {
                            this.datamodelmappings = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.sourceDatabaseMappingService.query({ 'sourceFeedMappingId.specified': 'false' }).subscribe(
            (res: HttpResponse<ISourceDatabaseMapping[]>) => {
                if (!this.sourceFeedMapping.sourceDatabaseMapping || !this.sourceFeedMapping.sourceDatabaseMapping.id) {
                    this.sourcedatabasemappings = res.body;
                } else {
                    this.sourceDatabaseMappingService.find(this.sourceFeedMapping.sourceDatabaseMapping.id).subscribe(
                        (subRes: HttpResponse<ISourceDatabaseMapping>) => {
                            this.sourcedatabasemappings = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.dataAssetService.query().subscribe(
            (res: HttpResponse<IDataAsset[]>) => {
                this.dataassets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sourceFeedMapping.id !== undefined) {
            this.subscribeToSaveResponse(this.sourceFeedMappingService.update(this.sourceFeedMapping));
        } else {
            this.subscribeToSaveResponse(this.sourceFeedMappingService.create(this.sourceFeedMapping));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISourceFeedMapping>>) {
        result.subscribe((res: HttpResponse<ISourceFeedMapping>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSourceFeedById(index: number, item: ISourceFeed) {
        return item.id;
    }

    trackDataModelMappingById(index: number, item: IDataModelMapping) {
        return item.id;
    }

    trackSourceDatabaseMappingById(index: number, item: ISourceDatabaseMapping) {
        return item.id;
    }

    trackDataAssetById(index: number, item: IDataAsset) {
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
    get sourceFeedMapping() {
        return this._sourceFeedMapping;
    }

    set sourceFeedMapping(sourceFeedMapping: ISourceFeedMapping) {
        this._sourceFeedMapping = sourceFeedMapping;
    }
}
