import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DataModel } from 'app/shared/model/data-model.model';
import { DataModelService } from './data-model.service';
import { DataModelComponent } from './data-model.component';
import { DataModelDetailComponent } from './data-model-detail.component';
import { DataModelUpdateComponent } from './data-model-update.component';
import { DataModelDeletePopupComponent } from './data-model-delete-dialog.component';
import { IDataModel } from 'app/shared/model/data-model.model';

@Injectable({ providedIn: 'root' })
export class DataModelResolve implements Resolve<IDataModel> {
    constructor(private service: DataModelService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((dataModel: HttpResponse<DataModel>) => dataModel.body));
        }
        return of(new DataModel());
    }
}

export const dataModelRoute: Routes = [
    {
        path: 'data-model',
        component: DataModelComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataModels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-model/:id/view',
        component: DataModelDetailComponent,
        resolve: {
            dataModel: DataModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataModels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-model/new',
        component: DataModelUpdateComponent,
        resolve: {
            dataModel: DataModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataModels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-model/:id/edit',
        component: DataModelUpdateComponent,
        resolve: {
            dataModel: DataModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataModels'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dataModelPopupRoute: Routes = [
    {
        path: 'data-model/:id/delete',
        component: DataModelDeletePopupComponent,
        resolve: {
            dataModel: DataModelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataModels'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
