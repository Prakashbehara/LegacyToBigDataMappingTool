import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFeed } from 'app/shared/model/feed.model';
import { FeedService } from './feed.service';
import { IApplication } from 'app/shared/model/application.model';
import { ApplicationService } from 'app/entities/application';

@Component({
    selector: 'jhi-feed-update',
    templateUrl: './feed-update.component.html'
})
export class FeedUpdateComponent implements OnInit {
    private _feed: IFeed;
    isSaving: boolean;

    applications: IApplication[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private feedService: FeedService,
        private applicationService: ApplicationService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ feed }) => {
            this.feed = feed;
        });
        this.applicationService.query().subscribe(
            (res: HttpResponse<IApplication[]>) => {
                this.applications = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.feed.id !== undefined) {
            this.subscribeToSaveResponse(this.feedService.update(this.feed));
        } else {
            this.subscribeToSaveResponse(this.feedService.create(this.feed));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFeed>>) {
        result.subscribe((res: HttpResponse<IFeed>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get feed() {
        return this._feed;
    }

    set feed(feed: IFeed) {
        this._feed = feed;
    }
}
