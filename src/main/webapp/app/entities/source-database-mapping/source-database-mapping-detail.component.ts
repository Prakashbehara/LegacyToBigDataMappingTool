import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISourceDatabaseMapping } from 'app/shared/model/source-database-mapping.model';

@Component({
    selector: 'jhi-source-database-mapping-detail',
    templateUrl: './source-database-mapping-detail.component.html'
})
export class SourceDatabaseMappingDetailComponent implements OnInit {
    sourceDatabaseMapping: ISourceDatabaseMapping;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sourceDatabaseMapping }) => {
            this.sourceDatabaseMapping = sourceDatabaseMapping;
        });
    }

    previousState() {
        window.history.back();
    }
}
