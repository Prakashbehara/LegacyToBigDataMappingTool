import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISourceFeed } from 'app/shared/model/source-feed.model';
import { Principal } from 'app/core';
import { SourceFeedService } from './source-feed.service';

@Component({
    selector: 'jhi-source-feed',
    templateUrl: './source-feed.component.html'
})
export class SourceFeedComponent implements OnInit, OnDestroy {
    sourceFeeds: ISourceFeed[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sourceFeedService: SourceFeedService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal,
    ) {}

    loadAll() {
        this.sourceFeedService.query().subscribe(
            (res: HttpResponse<ISourceFeed[]>) => {
                this.sourceFeeds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();

        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSourceFeeds();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISourceFeed) {
        return item.id;
    }

    registerChangeInSourceFeeds() {
        this.eventSubscriber = this.eventManager.subscribe('sourceFeedListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
