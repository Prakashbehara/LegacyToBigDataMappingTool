import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISourceDatabase } from 'app/shared/model/source-database.model';
import { Principal } from 'app/core';
import { SourceDatabaseService } from './source-database.service';

@Component({
    selector: 'jhi-source-database',
    templateUrl: './source-database.component.html'
})
export class SourceDatabaseComponent implements OnInit, OnDestroy {
    sourceDatabases: ISourceDatabase[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sourceDatabaseService: SourceDatabaseService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.sourceDatabaseService.query().subscribe(
            (res: HttpResponse<ISourceDatabase[]>) => {
                this.sourceDatabases = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSourceDatabases();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISourceDatabase) {
        return item.id;
    }

    registerChangeInSourceDatabases() {
        this.eventSubscriber = this.eventManager.subscribe('sourceDatabaseListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
