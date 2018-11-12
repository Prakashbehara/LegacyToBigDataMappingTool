import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISourceFeedMapping } from 'app/shared/model/source-feed-mapping.model';

@Component({
    selector: 'jhi-source-feed-mapping-detail',
    templateUrl: './source-feed-mapping-detail.component.html'
})
export class SourceFeedMappingDetailComponent implements OnInit {
    sourceFeedMapping: ISourceFeedMapping;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sourceFeedMapping }) => {
            this.sourceFeedMapping = sourceFeedMapping;
        });
    }

    previousState() {
        window.history.back();
    }
}
