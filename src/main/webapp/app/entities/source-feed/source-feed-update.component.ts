import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISourceFeed } from 'app/shared/model/source-feed.model';
import { SourceFeedService } from './source-feed.service';
import { IApplication } from 'app/shared/model/application.model';
import { ApplicationService } from 'app/entities/application';
import { ISourceDatabase } from 'app/shared/model/source-database.model';
import { SourceDatabaseService } from 'app/entities/source-database';

@Component({
    selector: 'jhi-source-feed-update',
    templateUrl: './source-feed-update.component.html'
})
export class SourceFeedUpdateComponent implements OnInit {
    private _sourceFeed: ISourceFeed;
    isSaving: boolean;

    applications: IApplication[];

    sourcedatabases: ISourceDatabase[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private sourceFeedService: SourceFeedService,
        private applicationService: ApplicationService,
        private sourceDatabaseService: SourceDatabaseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sourceFeed }) => {
            this.sourceFeed = sourceFeed;
        });
        this.applicationService.query().subscribe(
            (res: HttpResponse<IApplication[]>) => {
                this.applications = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.sourceDatabaseService.query({ 'sourceFeedId.specified': 'false' }).subscribe(
            (res: HttpResponse<ISourceDatabase[]>) => {
                if (!this.sourceFeed.sourceDatabase || !this.sourceFeed.sourceDatabase.id) {
                    this.sourcedatabases = res.body;
                } else {
                    this.sourceDatabaseService.find(this.sourceFeed.sourceDatabase.id).subscribe(
                        (subRes: HttpResponse<ISourceDatabase>) => {
                            this.sourcedatabases = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sourceFeed.id !== undefined) {
            this.subscribeToSaveResponse(this.sourceFeedService.update(this.sourceFeed));
        } else {
            this.subscribeToSaveResponse(this.sourceFeedService.create(this.sourceFeed));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISourceFeed>>) {
        result.subscribe((res: HttpResponse<ISourceFeed>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSourceDatabaseById(index: number, item: ISourceDatabase) {
        return item.id;
    }
    get sourceFeed() {
        return this._sourceFeed;
    }

    set sourceFeed(sourceFeed: ISourceFeed) {
        this._sourceFeed = sourceFeed;
    }
}
