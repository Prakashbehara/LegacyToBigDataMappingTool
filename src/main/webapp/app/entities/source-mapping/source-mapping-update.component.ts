import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISourceMapping } from 'app/shared/model/source-mapping.model';
import { SourceMappingService } from './source-mapping.service';
import { IFeed } from 'app/shared/model/feed.model';
import { FeedService } from 'app/entities/feed';
import { IDataModelMapping } from 'app/shared/model/data-model-mapping.model';
import { DataModelMappingService } from 'app/entities/data-model-mapping';
import { IDataAsset } from 'app/shared/model/data-asset.model';
import { DataAssetService } from 'app/entities/data-asset';

@Component({
    selector: 'jhi-source-mapping-update',
    templateUrl: './source-mapping-update.component.html'
})
export class SourceMappingUpdateComponent implements OnInit {
    private _sourceMapping: ISourceMapping;
    isSaving: boolean;

    feeds: IFeed[];

    datamodelmappings: IDataModelMapping[];

    dataassets: IDataAsset[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private sourceMappingService: SourceMappingService,
        private feedService: FeedService,
        private dataModelMappingService: DataModelMappingService,
        private dataAssetService: DataAssetService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sourceMapping }) => {
            this.sourceMapping = sourceMapping;
        });
        this.feedService.query().subscribe(
            (res: HttpResponse<IFeed[]>) => {
                this.feeds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.dataModelMappingService.query({ 'sourceMappingId.specified': 'false' }).subscribe(
            (res: HttpResponse<IDataModelMapping[]>) => {
                if (!this.sourceMapping.dataModelMapping || !this.sourceMapping.dataModelMapping.id) {
                    this.datamodelmappings = res.body;
                } else {
                    this.dataModelMappingService.find(this.sourceMapping.dataModelMapping.id).subscribe(
                        (subRes: HttpResponse<IDataModelMapping>) => {
                            this.datamodelmappings = [subRes.body].concat(res.body);
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
        if (this.sourceMapping.id !== undefined) {
            this.subscribeToSaveResponse(this.sourceMappingService.update(this.sourceMapping));
        } else {
            this.subscribeToSaveResponse(this.sourceMappingService.create(this.sourceMapping));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISourceMapping>>) {
        result.subscribe((res: HttpResponse<ISourceMapping>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackFeedById(index: number, item: IFeed) {
        return item.id;
    }

    trackDataModelMappingById(index: number, item: IDataModelMapping) {
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
    get sourceMapping() {
        return this._sourceMapping;
    }

    set sourceMapping(sourceMapping: ISourceMapping) {
        this._sourceMapping = sourceMapping;
    }
}
