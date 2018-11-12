import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISourceDatabase } from 'app/shared/model/source-database.model';

@Component({
    selector: 'jhi-source-database-detail',
    templateUrl: './source-database-detail.component.html'
})
export class SourceDatabaseDetailComponent implements OnInit {
    sourceDatabase: ISourceDatabase;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sourceDatabase }) => {
            this.sourceDatabase = sourceDatabase;
        });
    }

    previousState() {
        window.history.back();
    }
}
