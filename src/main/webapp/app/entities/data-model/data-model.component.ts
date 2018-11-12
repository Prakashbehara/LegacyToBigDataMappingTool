import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDataModel } from 'app/shared/model/data-model.model';
import { Principal } from 'app/core';
import { DataModelService } from './data-model.service';

@Component({
    selector: 'jhi-data-model',
    templateUrl: './data-model.component.html'
})
export class DataModelComponent implements OnInit, OnDestroy {
    dataModels: IDataModel[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private dataModelService: DataModelService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.dataModelService.query().subscribe(
            (res: HttpResponse<IDataModel[]>) => {
                this.dataModels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDataModels();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDataModel) {
        return item.id;
    }

    registerChangeInDataModels() {
        this.eventSubscriber = this.eventManager.subscribe('dataModelListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
