/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { SourceFeedMappingUpdateComponent } from 'app/entities/source-feed-mapping/source-feed-mapping-update.component';
import { SourceFeedMappingService } from 'app/entities/source-feed-mapping/source-feed-mapping.service';
import { SourceFeedMapping } from 'app/shared/model/source-feed-mapping.model';

describe('Component Tests', () => {
    describe('SourceFeedMapping Management Update Component', () => {
        let comp: SourceFeedMappingUpdateComponent;
        let fixture: ComponentFixture<SourceFeedMappingUpdateComponent>;
        let service: SourceFeedMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceFeedMappingUpdateComponent]
            })
                .overrideTemplate(SourceFeedMappingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SourceFeedMappingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceFeedMappingService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SourceFeedMapping(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sourceFeedMapping = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SourceFeedMapping();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sourceFeedMapping = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
