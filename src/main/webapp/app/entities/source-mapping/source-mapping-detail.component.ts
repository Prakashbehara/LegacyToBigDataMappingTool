import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISourceMapping } from 'app/shared/model/source-mapping.model';

@Component({
    selector: 'jhi-source-mapping-detail',
    templateUrl: './source-mapping-detail.component.html'
})
export class SourceMappingDetailComponent implements OnInit {
    sourceMapping: ISourceMapping;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sourceMapping }) => {
            this.sourceMapping = sourceMapping;
        });
    }

    previousState() {
        window.history.back();
    }
}
