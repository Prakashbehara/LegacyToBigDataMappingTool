import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISourceDatabaseMapping } from 'app/shared/model/source-database-mapping.model';
import { SourceDatabaseMappingService } from './source-database-mapping.service';
import { ISourceDatabase } from 'app/shared/model/source-database.model';
import { SourceDatabaseService } from 'app/entities/source-database';

@Component({
    selector: 'jhi-source-database-mapping-update',
    templateUrl: './source-database-mapping-update.component.html'
})
export class SourceDatabaseMappingUpdateComponent implements OnInit {
    private _sourceDatabaseMapping: ISourceDatabaseMapping;
    isSaving: boolean;

    sourcedatabases: ISourceDatabase[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private sourceDatabaseMappingService: SourceDatabaseMappingService,
        private sourceDatabaseService: SourceDatabaseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sourceDatabaseMapping }) => {
            this.sourceDatabaseMapping = sourceDatabaseMapping;
        });
        this.sourceDatabaseService.query().subscribe(
            (res: HttpResponse<ISourceDatabase[]>) => {
                this.sourcedatabases = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sourceDatabaseMapping.id !== undefined) {
            this.subscribeToSaveResponse(this.sourceDatabaseMappingService.update(this.sourceDatabaseMapping));
        } else {
            this.subscribeToSaveResponse(this.sourceDatabaseMappingService.create(this.sourceDatabaseMapping));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISourceDatabaseMapping>>) {
        result.subscribe(
            (res: HttpResponse<ISourceDatabaseMapping>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackSourceDatabaseById(index: number, item: ISourceDatabase) {
        return item.id;
    }
    get sourceDatabaseMapping() {
        return this._sourceDatabaseMapping;
    }

    set sourceDatabaseMapping(sourceDatabaseMapping: ISourceDatabaseMapping) {
        this._sourceDatabaseMapping = sourceDatabaseMapping;
    }
}
