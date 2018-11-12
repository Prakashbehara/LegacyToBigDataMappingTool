import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DataAsset } from 'app/shared/model/data-asset.model';
import { DataAssetService } from './data-asset.service';
import { DataAssetComponent } from './data-asset.component';
import { DataAssetDetailComponent } from './data-asset-detail.component';
import { DataAssetUpdateComponent } from './data-asset-update.component';
import { DataAssetDeletePopupComponent } from './data-asset-delete-dialog.component';
import { IDataAsset } from 'app/shared/model/data-asset.model';

@Injectable({ providedIn: 'root' })
export class DataAssetResolve implements Resolve<IDataAsset> {
    constructor(private service: DataAssetService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((dataAsset: HttpResponse<DataAsset>) => dataAsset.body));
        }
        return of(new DataAsset());
    }
}

export const dataAssetRoute: Routes = [
    {
        path: 'data-asset',
        component: DataAssetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataAssets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-asset/:id/view',
        component: DataAssetDetailComponent,
        resolve: {
            dataAsset: DataAssetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataAssets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-asset/new',
        component: DataAssetUpdateComponent,
        resolve: {
            dataAsset: DataAssetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataAssets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-asset/:id/edit',
        component: DataAssetUpdateComponent,
        resolve: {
            dataAsset: DataAssetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataAssets'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dataAssetPopupRoute: Routes = [
    {
        path: 'data-asset/:id/delete',
        component: DataAssetDeletePopupComponent,
        resolve: {
            dataAsset: DataAssetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataAssets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
