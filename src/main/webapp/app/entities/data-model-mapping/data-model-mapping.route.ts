import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DataModelMapping } from 'app/shared/model/data-model-mapping.model';
import { DataModelMappingService } from './data-model-mapping.service';
import { DataModelMappingComponent } from './data-model-mapping.component';
import { DataModelMappingDetailComponent } from './data-model-mapping-detail.component';
import { DataModelMappingUpdateComponent } from './data-model-mapping-update.component';
import { DataModelMappingDeletePopupComponent } from './data-model-mapping-delete-dialog.component';
import { IDataModelMapping } from 'app/shared/model/data-model-mapping.model';

@Injectable({ providedIn: 'root' })
export class DataModelMappingResolve implements Resolve<IDataModelMapping> {
    constructor(private service: DataModelMappingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((dataModelMapping: HttpResponse<DataModelMapping>) => dataModelMapping.body));
        }
        return of(new DataModelMapping());
    }
}

export const dataModelMappingRoute: Routes = [
    {
        path: 'data-model-mapping',
        component: DataModelMappingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataModelMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-model-mapping/:id/view',
        component: DataModelMappingDetailComponent,
        resolve: {
            dataModelMapping: DataModelMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataModelMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-model-mapping/new',
        component: DataModelMappingUpdateComponent,
        resolve: {
            dataModelMapping: DataModelMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataModelMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-model-mapping/:id/edit',
        component: DataModelMappingUpdateComponent,
        resolve: {
            dataModelMapping: DataModelMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataModelMappings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dataModelMappingPopupRoute: Routes = [
    {
        path: 'data-model-mapping/:id/delete',
        component: DataModelMappingDeletePopupComponent,
        resolve: {
            dataModelMapping: DataModelMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataModelMappings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
