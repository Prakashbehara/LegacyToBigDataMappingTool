import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SourceFeedMapping } from 'app/shared/model/source-feed-mapping.model';
import { SourceFeedMappingService } from './source-feed-mapping.service';
import { SourceFeedMappingComponent } from './source-feed-mapping.component';
import { SourceFeedMappingDetailComponent } from './source-feed-mapping-detail.component';
import { SourceFeedMappingUpdateComponent } from './source-feed-mapping-update.component';
import { SourceFeedMappingDeletePopupComponent } from './source-feed-mapping-delete-dialog.component';
import { ISourceFeedMapping } from 'app/shared/model/source-feed-mapping.model';

@Injectable({ providedIn: 'root' })
export class SourceFeedMappingResolve implements Resolve<ISourceFeedMapping> {
    constructor(private service: SourceFeedMappingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((sourceFeedMapping: HttpResponse<SourceFeedMapping>) => sourceFeedMapping.body));
        }
        return of(new SourceFeedMapping());
    }
}

export const sourceFeedMappingRoute: Routes = [
    {
        path: 'source-feed-mapping',
        component: SourceFeedMappingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceFeedMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-feed-mapping/:id/view',
        component: SourceFeedMappingDetailComponent,
        resolve: {
            sourceFeedMapping: SourceFeedMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceFeedMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-feed-mapping/new',
        component: SourceFeedMappingUpdateComponent,
        resolve: {
            sourceFeedMapping: SourceFeedMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceFeedMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-feed-mapping/:id/edit',
        component: SourceFeedMappingUpdateComponent,
        resolve: {
            sourceFeedMapping: SourceFeedMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceFeedMappings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sourceFeedMappingPopupRoute: Routes = [
    {
        path: 'source-feed-mapping/:id/delete',
        component: SourceFeedMappingDeletePopupComponent,
        resolve: {
            sourceFeedMapping: SourceFeedMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceFeedMappings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
