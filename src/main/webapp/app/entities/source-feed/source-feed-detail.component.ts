import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISourceFeed } from 'app/shared/model/source-feed.model';

@Component({
    selector: 'jhi-source-feed-detail',
    templateUrl: './source-feed-detail.component.html'
})
export class SourceFeedDetailComponent implements OnInit {
    sourceFeed: ISourceFeed;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sourceFeed }) => {
            this.sourceFeed = sourceFeed;
        });
    }

    previousState() {
        window.history.back();
    }
}
