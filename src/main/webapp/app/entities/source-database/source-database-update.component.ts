import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISourceDatabase } from 'app/shared/model/source-database.model';
import { SourceDatabaseService } from './source-database.service';
import { IApplication } from 'app/shared/model/application.model';
import { ApplicationService } from 'app/entities/application';

@Component({
    selector: 'jhi-source-database-update',
    templateUrl: './source-database-update.component.html'
})
export class SourceDatabaseUpdateComponent implements OnInit {
    private _sourceDatabase: ISourceDatabase;
    isSaving: boolean;

    applications: IApplication[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private sourceDatabaseService: SourceDatabaseService,
        private applicationService: ApplicationService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sourceDatabase }) => {
            this.sourceDatabase = sourceDatabase;
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
        if (this.sourceDatabase.id !== undefined) {
            this.subscribeToSaveResponse(this.sourceDatabaseService.update(this.sourceDatabase));
        } else {
            this.subscribeToSaveResponse(this.sourceDatabaseService.create(this.sourceDatabase));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISourceDatabase>>) {
        result.subscribe((res: HttpResponse<ISourceDatabase>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get sourceDatabase() {
        return this._sourceDatabase;
    }

    set sourceDatabase(sourceDatabase: ISourceDatabase) {
        this._sourceDatabase = sourceDatabase;
    }
}
